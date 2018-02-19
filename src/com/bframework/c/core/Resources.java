package com.bframework.c.core;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Resources {
	
	public static final String SOUND_FILE_ENDING = ".wav", IMAGE_FILE_ENDING = ".png";
	
	public static void load() {
		loadSounds();
		loadImages();
	}
	
	/* SOUND */
	
	private static void loadSounds() {
		System.out.println("> Loading Sounds from 'sounds' package");
		try {
			String name;
			BufferedReader br = new BufferedReader(new InputStreamReader(Resources.class.getClassLoader().getResourceAsStream("sounds")));
			while ((name = br.readLine()) != null) {
				if (name.endsWith(SOUND_FILE_ENDING)) {
					URL url = Resources.class.getClassLoader().getResource("sounds/" + name);
					AudioClip audioClip = Applet.newAudioClip(url);
					sounds.put(name, audioClip);
					System.out.println(" + Sound Loaded: " + name);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(" * Missing 'sounds' package");
		}
	}
	
	private static HashMap<String, AudioClip> sounds = new HashMap<String, AudioClip>();
	private static ArrayList<String> looping = new ArrayList<String>();

	public static void play(String name) {
		try {
			sounds.get(name + SOUND_FILE_ENDING).play();	
		} catch (NullPointerException e) {
			System.err.println(String.format("Missing Sound File: %s.wav", name));
		}
	}

	public static void loop(String name) {
		try {
			sounds.get(name + SOUND_FILE_ENDING).loop();
			looping.add(name);
		} catch (NullPointerException e) {
			System.err.println(String.format("Missing Sound File: %s.wav", name));
		}
	}

	public static void stop(String name) {
		try {
			sounds.get(name + SOUND_FILE_ENDING).stop();
			looping.remove(name);
		} catch (NullPointerException e) {
			System.err.println(String.format("Missing Sound File: %s.wav", name));
		}
	}

	public static void stopAll() {
		for (int i = 0; i < looping.size(); i++) {
			stop(looping.get(i));
		}
	}

	/* IMAGES */
	
	private static HashMap<String,BufferedImage> images = new HashMap<String,BufferedImage>();
	
	public static HashMap<String,BufferedImage> images() {
		return images;
	}
	
	public static void images(HashMap<String,BufferedImage> value) {
		Resources.images = value;
	}
	
	public static BufferedImage image(String name) {
		return images.get(name + IMAGE_FILE_ENDING);
	}
	
	private static void loadImages() {
		System.out.println("> Loading Images from 'images' package");
		try {
			String name;
			BufferedReader br = new BufferedReader(new InputStreamReader(Resources.class.getClassLoader().getResourceAsStream("images")));
			while ((name = br.readLine()) != null) {
				if (name.endsWith(IMAGE_FILE_ENDING)) {
					URL url = Resources.class.getClassLoader().getResource("images/" + name);
					BufferedImage image = ImageIO.read(url);
					images.put(name, image);
					System.out.println(" + Image Loaded: " + name);
				}
			}
		} catch (NullPointerException e) {
			System.out.println(" * Missing 'images' package");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
