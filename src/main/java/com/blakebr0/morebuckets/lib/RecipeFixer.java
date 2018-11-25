package com.blakebr0.morebuckets.lib;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.blakebr0.morebuckets.item.ItemMoreBucket;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeFixer {
	
	public static final ArrayList<ItemMoreBucket> VALID_BUCKETS = new ArrayList<>();
	
	public static void fixRecipes() { // TODO: REMOVE 
		ForgeRegistries.RECIPES.register(new ShapelessRecipes("wow", new ItemStack(Items.ACACIA_BOAT), NonNullList.<Ingredient>withSize(1, Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)))).setRegistryName("testerino"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation("hello", "there"), new ItemStack(Items.DARK_OAK_BOAT), new ItemStack(Items.LAVA_BUCKET)).setRegistryName("tester"));
		ForgeRegistries.RECIPES.forEach(recipe -> {
			NonNullList<Ingredient> ing = recipe.getIngredients();
			boolean shapeless = false;
			boolean shapelessOre = false;
			for (int i = 0; i < ing.size(); i++) {
				Ingredient ingredient = ing.get(i);
				for (ItemStack stack : ingredient.getMatchingStacks()) {
					 if (stack.getItem() instanceof UniversalBucket || stack.getItem() == Items.LAVA_BUCKET || stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.MILK_BUCKET) {
						 ing.set(i, new FluidIngredient(ingredient));
						 if (recipe instanceof ShapelessRecipes) shapeless = true;
						 if (recipe instanceof ShapelessOreRecipe) shapelessOre = true;
						 break;
					 }
				}
			}
			
			if (shapeless) {
				setNotSimple((ShapelessRecipes) recipe);
			}
			
			if (shapelessOre) {
				setNotSimple((ShapelessOreRecipe) recipe);
			}
		});
	}
	
	private static void setNotSimple(ShapelessRecipes recipe) {
		try {
			Field isSimple = recipe.getClass().getDeclaredField("isSimple");
			isSimple.setAccessible(true);
			isSimple.set(recipe, false);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private static void setNotSimple(ShapelessOreRecipe recipe) {
		try {
			Field isSimple = recipe.getClass().getDeclaredField("isSimple");
			isSimple.setAccessible(true);
			isSimple.set(recipe, false);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}