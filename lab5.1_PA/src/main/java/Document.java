


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Document implements Serializable {

    private String uniqueID = UUID.randomUUID().toString();
    private String name;
    private Map<String, String> tags;
    private DocumentType type;
    private String documentLocation;

    public Document(String name, DocumentType type, String documentLocation) {
        this.name = name;
        this.type = type;
        this.documentLocation = documentLocation;
        this.tags = new LinkedHashMap<>();
    }

    public void addTag(String key, String value) throws KeyOverwriteException {
        if(tags.containsKey(key)){
            throw new KeyOverwriteException(key);
        }else{
            tags.put(key, value);
        }
    }

    public void modifyTag(String key, String value) throws KeyDoesNotExistException {
        if(!tags.containsKey(key)){
            throw new KeyDoesNotExistException(key);
        }else{
            tags.put(key, value);
        }
    }

    public void deleteTag(String key) throws KeyDoesNotExistException {
        if(!tags.containsKey(key)){
            throw new KeyDoesNotExistException(key);
        }else{
            tags.remove(key);
        }
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getTags() {
        Map<String, String> copyOfTags = new LinkedHashMap<>();
        copyOfTags.putAll(tags);
        return copyOfTags;
    }

    public DocumentType getType() {
        return type;
    }

    public String getDocumentLocation() {
        return documentLocation;
    }

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", documentLocation='" + documentLocation + '\'' +
                '}';
    }
}
