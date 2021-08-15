package com.gmail.diviegg.Handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class ItemStackHandler {
  public static String ItemStackToString(ItemStack itemStack) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      BukkitObjectOutputStream data = new BukkitObjectOutputStream(outputStream);
      data.writeObject(itemStack);
      data.close();
      return String.valueOf(Base64Coder.encode(outputStream.toByteArray()));
    } catch (IOException ignored) {
      return "";
    } 
  }
  
  public static ItemStack ItemStackFromString(String itemStack) {
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decode(itemStack));
      BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
      ItemStack itemArray = (ItemStack)dataInput.readObject();
      dataInput.close();
      return itemArray;
    } catch (ClassNotFoundException|IOException ignored) {
      return null;
    } 
  }
}
