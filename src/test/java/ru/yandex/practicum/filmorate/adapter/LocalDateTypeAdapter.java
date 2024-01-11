package ru.yandex.practicum.filmorate.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTypeAdapter implements JsonSerializer<LocalDate> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

  @Override
  public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(localDate.format(FORMATTER));
  }
}
