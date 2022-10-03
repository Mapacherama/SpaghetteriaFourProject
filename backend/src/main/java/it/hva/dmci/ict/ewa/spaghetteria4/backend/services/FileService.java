package it.hva.dmci.ict.ewa.spaghetteria4.backend.services;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.File;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Image;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.FileRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class FileService {
    private final ImageRepository imageRepository;
    private final FileRepository fileRepository;

    public FileService(ImageRepository imageRepository, FileRepository fileRepository) {
        this.imageRepository = imageRepository;
        this.fileRepository = fileRepository;
    }

    /**
     *
     * @param multipartFile -  File to be saved
     * @return ID of file in database
     */
    public Long uploadFile(MultipartFile multipartFile) {
        File file;

        try {
            file = new File(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                    compressBytes(multipartFile.getBytes()));
        } catch (IOException e) {
            return null;
        }
        fileRepository.save(file);
        return fileRepository.save(file).getId();
    }

    /**
     *
     * @param multipartFile - Image to be saved
     * @return ID of image in DB
     */
    public Long uploadImage(MultipartFile multipartFile) {
        Image img;

        try {
            img = new Image(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                    compressBytes(multipartFile.getBytes()));
        } catch (IOException e) {
            return null;
        }
        imageRepository.save(img);
        return imageRepository.save(img).getId();
    }

    /**
     *
     * @param id - ID of file to get
     * @return file with corresponding ID
     */
    public File getFile(Long id) {
        final Optional<File> retrievedFile = fileRepository.findById(id);
        File file = new File(retrievedFile.get().getName(), retrievedFile.get().getType(),
                decompressBytes(retrievedFile.get().getBytes()));
        return file;
    }

    /**
     *
     * @param id - ID of image to get
     * @return image with corresponding ID
     */
    public Image getImage(Long id) {
        final Optional<Image> retrievedImage = imageRepository.findById(id);
        Image img = new Image(retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getBytes()));
        return img;
    }

    /**
     * Compress bytes for smaller size
     * @param data - Bytes to compress
     * @return compressed bytes
     */
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException ignored) { }
        return outputStream.toByteArray();
    }

    /**
     * Decompress compressed bytes
     * @param data - Bytes to decompress
     * @return decompressed bytes
     */
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ignored) {
        }
        return outputStream.toByteArray();
    }
}
