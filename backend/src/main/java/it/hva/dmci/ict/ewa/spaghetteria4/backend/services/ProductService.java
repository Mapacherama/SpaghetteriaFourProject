package it.hva.dmci.ict.ewa.spaghetteria4.backend.services;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Product;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.ProductDto;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.ProductRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProductsForCategory(String category){
        List<Product> productsPerCategory = productRepository.findAllForCategory(category);
       return productsPerCategory;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product save(Product p) {
        return productRepository.save(p);
    }

    public boolean deleteProductById(Long id){
        productRepository.deleteById(id);
        return productRepository.existsById(id);
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public byte[] generateMenu(List<ProductDto> products) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("menu.xlsx");
        Workbook wb = WorkbookFactory.create(inputStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Sheet worksheet = wb.getSheetAt(0);

        int row = 24;
        int colNameDesc = 1;
        int colPrice = 7;
        for (int i = 0; i < 7; i++) {
            Cell name = worksheet.getRow(row).getCell(colNameDesc);
            Cell price = worksheet.getRow(row).getCell(colPrice);
            Cell desc = worksheet.getRow(row + 1).getCell(colNameDesc);

            name.setCellValue(products.get(i).getName());
            price.setCellValue(products.get(i).getPrice());
            desc.setCellValue(products.get(i).getDesc());

            row += i == 5 ? 4 : 3;
        }

        wb.write(baos);

        return baos.toByteArray();
    }
}
