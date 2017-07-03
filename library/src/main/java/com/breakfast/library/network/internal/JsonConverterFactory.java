package com.breakfast.library.network.internal;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public class JsonConverterFactory extends Converter.Factory {

  private final Gson gson;

  public static JsonConverterFactory create(Gson gson) {
    return new JsonConverterFactory(gson);
  }

  private JsonConverterFactory(Gson gson) {
    this.gson = gson;
  }

  /**
   * Returns a {@link Converter} for converting an HTTP response body to {@code type}, or null if
   * {@code type} cannot be handled by this factory. This is used to create converters for
   * response types such as {@code SimpleResponse} from a {@code Call<SimpleResponse>}
   * declaration.
   */
  public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
      Retrofit retrofit) {
    return new JsonResponseBodyConverter<>(gson, type);
  }

  /**
   * Returns a {@link Converter} for converting {@code type} to an HTTP request body, or null if
   * {@code type} cannot be handled by this factory. This is used to create converters for types
   * specified by {@link Body @Body}, {@link Part @Part}, and {@link PartMap @PartMap}
   * values.
   */
  public Converter<?, RequestBody> requestBodyConverter(Type type,
      Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
    TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
    return new JsonRequestBodyConverter<>(gson, adapter);
  }

//  /**
//   * Returns a {@link Converter} for converting {@code type} to a {@link String}, or null if
//   * {@code type} cannot be handled by this factory. This is used to create converters for types
//   * specified by {@link Field @Field}, {@link FieldMap @FieldMap} values,
//   * {@link Header @Header}, {@link HeaderMap @HeaderMap}, {@link Path @Path},
//   * {@link Query @Query}, and {@link QueryMap @QueryMap} values.
//   */
//  public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
//      Retrofit retrofit) {
//    return new JsonResponseBodyConverter<>(gson, type);
//  }
}