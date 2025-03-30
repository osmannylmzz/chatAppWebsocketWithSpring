package project.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Groups")
public class Groups {

    @Id
    private String id; // MongoDB'de ID genelde String olarak kullanılır
    private String name;
    private List<String> kullanıcılar;

    // Getter ve Setter metodları
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getKullanıcılar() {
        return kullanıcılar;
    }

    public void setKullanıcılar(List<String> kullanıcılar) {
        this.kullanıcılar = kullanıcılar;
    }
}
