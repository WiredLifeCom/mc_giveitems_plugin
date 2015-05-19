package com.wiredlife.mcplugin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.wiredlife.jsonformatjava.dba.DBA;
import com.wiredlife.jsonformatjava.model.unload.Unload;

public class StorageController {

	private static Map<String, Material> materialMappings;

	private DBA dba;

	public StorageController(String database) {
		if (materialMappings == null) {
			materialMappings = new HashMap<String, Material>();
			materialMappings.put("Dirt", Material.DIRT);
			materialMappings.put("Stone", Material.STONE);
			materialMappings.put("DiamondPickaxe", Material.DIAMOND_PICKAXE);
			materialMappings.put("WoodenAxe", Material.WOOD_AXE);
		}

		this.dba = new DBA(database);
	}

	public List<Unload> getUnloads(String username) {
		return this.dba.getUnloads(username);
	}

	public void deleteUnloads(String username) {
		this.dba.deleteUnloads(username);
	}

	public void closeDatabaseConnection() {
		this.dba.close();
	}

	public synchronized void updateResources(Player player, Unload unload) {
		// Get the materials
		List<String> materials = unload.getMaterials();

		// The player's inventory
		PlayerInventory playerInventory = player.getInventory();

		// Loop through every material and add these to the player inventory
		for (String material : materials) {
			ItemStack itemStack = new ItemStack(getMaterialMappings().get(material), 1);
			playerInventory.addItem(itemStack);
		}
	}

	public Map<String, Material> getMaterialMappings() {
		return StorageController.materialMappings;
	}

}
