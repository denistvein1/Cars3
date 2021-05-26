package Domain;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Car {

    private final String company, model;
    private final int year;
    private String link;

    public Car(String company, String model, int year) {
        this.company = company;
        this.model = model;
        this.year = year;
        this.link = "----------";
    }

    public Car(String company, String model, int year, String link) {
        this(company, model, year);
        this.link = link;
    }

    public String getCompany() {
        return company;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String toStringNoLink() {
        return "Car{" +
                "company='" + company + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }

    @Override
    public String toString() {
        return company + ',' + model + ',' + year + ',' + link + "\n";
    }

    public static void writeToHtmlFile(FileWriter file, Car car) throws IOException {
        file.write("<html>\n");
        file.write("<body>\n");
        file.write("<h2>" + car.getCompany() + "</h2>\n");
        file.write("<p>" + car.getModel() + ", " + car.getYear() + "</p>\n");
        file.write("<a href=" + car.getLink() + ">Learn more</a>\n");
        file.write("</body>\n");
        file.write("</html>\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return getYear() == car.getYear() && Objects.equals(getCompany(), car.getCompany()) && Objects.equals(getModel(), car.getModel());
    }
}
