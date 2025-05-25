package testing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseProducts {
    @JsonProperty("id")
    public int id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("data")
    public ProductData data;

    public ResponseProducts() {

    }

    // Constructor
    public ResponseProducts(int id, String name, ProductData data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public static class ProductData {

        @JsonProperty("year")
        public String year;

        @JsonProperty("price")
        public double price;

        @JsonProperty("cpu_model")
        public String cpuModel;

        @JsonProperty("hard_disk_size")
        public String hardDiskSize;

        @JsonProperty("capacity")
        public String capacity;

        @JsonProperty("screen_size")
        public String screenSize;

        @JsonProperty("color")
        public String color;

        public ProductData(){

        }
        // Constructor
        public ProductData(String year, double price, String cpuModel, String hardDiskSize,
                           String capacity, String screenSize, String color) {
            this.year = year;
            this.price = price;
            this.cpuModel = cpuModel;
            this.hardDiskSize = hardDiskSize;
            this.capacity = capacity;
            this.screenSize = screenSize;
            this.color = color;
        }
    }
}
