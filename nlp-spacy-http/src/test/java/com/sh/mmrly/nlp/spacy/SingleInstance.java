package com.sh.mmrly.nlp.spacy;

import org.jetbrains.annotations.NotNull;

import javax.enterprise.inject.Instance;
import javax.enterprise.util.TypeLiteral;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Iterator;

class SingleInstance<T> implements Instance<T> {
  private final T instance;

  SingleInstance(T instance) {
    this.instance = instance;
  }

  public static <E> Instance<E> of(E instance) {
    return new SingleInstance<>(instance);
  }

  @Override
  public T get() {
    return instance;
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return Arrays.asList(get()).iterator();
  }

  @Override
  public Instance<T> select(Annotation... qualifiers) {
    return this;
  }

  @Override
  public <U extends T> Instance<U> select(Class<U> subtype, Annotation... qualifiers) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <U extends T> Instance<U> select(TypeLiteral<U> subtype, Annotation... qualifiers) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isUnsatisfied() {
    return false;
  }

  @Override
  public boolean isAmbiguous() {
    return false;
  }

  @Override
  public void destroy(T instance) {

  }
}
