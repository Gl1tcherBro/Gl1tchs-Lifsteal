package gl1tch.lifesteal;

import gl1tch.lifesteal.util.ModStuffs;
import net.fabricmc.api.ModInitializer;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

public class Lifesteal implements ModInitializer {
	public static final String MOD_ID = "lifesteal";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModStuffs.register();
	}
}
