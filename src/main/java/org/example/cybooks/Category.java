package org.example.cybooks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Category {
    private static final String JSON_FILE_PATH = "json/categories.json";

    private Map<String, String> categories;

    public Category() {
        categories = loadCategories();
    }
    private Map<String, String> loadCategories() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> categoriesMap = new HashMap<>();
        try {
            File file = new File(JSON_FILE_PATH);
            if (!file.exists()) {
                System.err.println("Failed to load categories: File not found");
                return categoriesMap;
            }
            List<Map<String, String>> categoriesList = objectMapper.readValue(file, new TypeReference<List<Map<String, String>>>() {});
            for (Map<String, String> category : categoriesList) {
                categoriesMap.put(category.get("code"), category.get("libelle"));
            }
        } catch (IOException e) {
            System.err.println("Failed to load categories: " + e.getMessage());
        }
        return categoriesMap;
    }
    public String getLibelle(String code) {
        return categories.getOrDefault(code, "Catégorie introuvable");
    }
}
