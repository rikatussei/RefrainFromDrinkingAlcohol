package dto;

public class PresetMonsterDTO {
    private int id;
    private String name;
    private String imagePath;
    private int baseHp;
    private String description;

    // コンストラクタ
    public PresetMonsterDTO() {}

    public PresetMonsterDTO(int id, String name, String imagePath, int baseHp, String description) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.baseHp = baseHp;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public int getBaseHp() { return baseHp; }
    public void setBaseHp(int baseHp) { this.baseHp = baseHp; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "PresetMonsterDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", baseHp=" + baseHp +
                ", description='" + description + '\'' +
                '}';
    }
}