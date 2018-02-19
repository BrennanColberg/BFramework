package com.bframework.c.sprites.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bframework.c.graphics.Poly;
import com.bframework.c.math.Vector;

public class SpriteIO {
	
	
	/* VARIABLES */
	
	private static HashMap<String, Sprite> sprites = new HashMap<String,Sprite>();
	
	
	/* GETTERS & SETTERS */
	
	public static Sprite get(String key) {
		return sprites.get(key);
	}
	public static Sprite get(int index) {
		return get(sprites.keySet().toArray(new String[]{})[index]);
	}
	public static HashMap<String, Sprite> sprites() {
		return sprites;
	}
	
	public static int quantity() {
		return sprites.size();
	}
	
	public static String[] keys() {
		return sprites.keySet().toArray(new String[]{});
	}
	
	
	/* METHODS */
	
	public static void load() {
		System.out.println("> Loading Sprites from 'sprites' package");
		try {
			String name;
			BufferedReader br = new BufferedReader(new InputStreamReader(SpriteIO.class.getClassLoader().getResourceAsStream("sprites")));
			while ((name = br.readLine()) != null) {
				if (name.endsWith(".txt")) {
					String title = name.substring(0, name.length() - 4);
					sprites.put(title, read(name));
					System.out.println(" + Sprite Loaded: " + title);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(" * Missing 'sprites' package");
		}
	}
	
	public static Sprite read(String file) {
		BufferedReader parser = new BufferedReader(new InputStreamReader(SpriteIO.class.getClassLoader().getResourceAsStream("sprites/" + file)));
		Sprite sprite = new Sprite();
		String text;
		String[] splitText;
		Poly currentPoly = null;
		
		try {
			while ((text = parser.readLine()) != null) {
				if (text != "") {
					splitText = text.split(" ");
					switch(splitText[0].toUpperCase()) {
					case "NEW":
						if (currentPoly != null && !sprite.contains(currentPoly)) {
							sprite.add(currentPoly);
						}
						break;
					case "IMPORT":
						if (currentPoly != null && !sprite.contains(currentPoly)) sprite.add(currentPoly);
						try { currentPoly = read(splitText[1]).get(splitText.length > 2 ? Integer.parseInt(splitText[2]) : 0); }
						catch (Exception e) { System.err.println(String.format("%s during import from %s to %s", e.getClass().getName(), splitText[1], file)); }
						break;
					case "POINT":
						if (currentPoly == null || sprite.contains(currentPoly)) currentPoly = new Poly();
						currentPoly.addVertex(new Vector(
								Double.parseDouble(splitText[1]),
								Double.parseDouble(splitText[2])));
						break;
					case "SCALE":
						currentPoly.scale(Double.parseDouble(splitText[1]));
						break;
					case "ROTATE":
						currentPoly.rotate(Double.parseDouble(splitText[1]));
						break;
					case "ROTATE_PI":
						currentPoly.rotate(Double.parseDouble(splitText[1]) * Math.PI);
						break;
					case "POSITION":
						currentPoly.position = new Vector(Double.parseDouble(splitText[1]), Double.parseDouble(splitText[2]));
						break;
					case "SHOW_HEALTH":
					case "HEALTH":
						sprite.showHealth(sprite.size(), Boolean.parseBoolean(splitText[1]));
						System.out.println("put health " + sprite.showHealth(sprite.size()) + " for " + sprite + " layer " + sprite.size());
						break;
					case "SHADE":
						sprite.shade(sprite.size(), Double.parseDouble(splitText[1]));
						break;
					}
				}
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		if (currentPoly != null && !sprite.contains(currentPoly)) sprite.add(currentPoly);
		
		return sprite;
	}
	@Deprecated public static void write(Sprite sprite) {
		System.out.println(String.format("Printing %s", sprite));
		List<String> writingQueue = new ArrayList<String>();
		for (int i = 0; i < sprite.size(); i++) {
			writingQueue.add("NEW");
			for (int v = 0; v < sprite.get(i).vertices().size(); v++)
				writingQueue.add(String.format("POINT %s %s", sprite.get(i).vertex(v).value[0], sprite.get(i).vertex(v).value[1]));
		}
		
		for (String line:writingQueue) System.out.println(line);
		
		System.out.println(String.format("Finished printing %s", sprite));
	}
	@Deprecated public static void write(Poly poly) {
		Sprite drawable = new Sprite();
		drawable.add(poly);
		write(drawable);
	}
}
