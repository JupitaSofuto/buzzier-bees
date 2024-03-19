package com.teamabnormals.buzzier_bees.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.loot.modification.LootModifierProvider;
import com.teamabnormals.blueprint.common.loot.modification.modifiers.LootPoolEntriesModifier;
import com.teamabnormals.buzzier_bees.core.BuzzierBees;
import com.teamabnormals.buzzier_bees.core.registry.BBItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class BBLootModifierProvider extends LootModifierProvider {

	public BBLootModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(BuzzierBees.MOD_ID, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		this.entry("jungle_temple").selects(BuiltInLootTables.JUNGLE_TEMPLE).addModifier(new LootPoolEntriesModifier(false, 0, Collections.singletonList(LootItem.lootTableItem(BBItems.FOUR_LEAF_CLOVER.get()).setWeight(5).build())));
		this.entry("desert_pyramid").selects(BuiltInLootTables.DESERT_PYRAMID)
				.addModifier(new LootPoolEntriesModifier(false, 0, Collections.singletonList(LootItem.lootTableItem(BBItems.FOUR_LEAF_CLOVER.get()).setWeight(5).build())))
				.addModifier(new LootPoolEntriesModifier(false, 1, Collections.singletonList(LootItem.lootTableItem(Items.HONEY_BOTTLE).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).build())));
	}
}