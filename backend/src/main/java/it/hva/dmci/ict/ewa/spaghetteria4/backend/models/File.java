package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class File {

    public File() {
        super();
    }

    public File(String name, String type, byte[] bytes) {
        this.name = name;
        this.type = type;
        this.bytes = bytes;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "bytes")
    @Lob
    private byte[] bytes;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] picByte) {
        this.bytes = picByte;
    }
}
