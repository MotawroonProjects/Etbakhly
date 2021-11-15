package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class BuffetModel implements Serializable {
  private int id;
  private String titel;
  private String photo;
  private int number_people;
  private int order_time;
  private String service_provider_type;
  private int price;
  private int category_dishes_id;
  private String created_at;
  private String updated_at;
  private List<CategorDish> categor_dishes;

  public int getId() {
    return id;
  }

  public String getTitel() {
    return titel;
  }

  public String getPhoto() {
    return photo;
  }

  public int getNumber_people() {
    return number_people;
  }

  public int getOrder_time() {
    return order_time;
  }

  public String getService_provider_type() {
    return service_provider_type;
  }

  public int getPrice() {
    return price;
  }

  public int getCategory_dishes_id() {
    return category_dishes_id;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public List<CategorDish> getCategor_dishes() {
    return categor_dishes;
  }
}
