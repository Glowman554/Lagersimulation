package benutzerschnittstelle;

import io.github.glowman554.nudel.plugin.Plugin;

public class Discord implements Plugin
{

	@Override
	public void on_load() throws Exception
	{
		DiscordCommand command = new DiscordCommand();
		io.github.glowman554.nudel.discord.Discord.discord.commandManager.addCommand("lagersimulation", command);
		io.github.glowman554.nudel.discord.Discord.discord.commandManager.addSlashCommand("lagersimulation", command);
	}

}
