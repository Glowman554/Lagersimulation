package benutzerschnittstelle;

import java.io.IOException;
import java.util.HashMap;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class DiscordCommand implements Command, SlashCommand
{

	HashMap<String, DiscordState> state = new HashMap<>();
	
	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 1)
		{
			event.commandFail("Missing count to buy");
		}
		else
		{
			if (state.get(event.event.getAuthor().getId()) == null)
			{
				state.put(event.event.getAuthor().getId(), new DiscordState());
			}
			
			DiscordState curr = state.get(event.event.getAuthor().getId());
			
			int eingekaufteMenge = Integer.parseInt(event.args[0]);

			double kosten_for_haenlder = curr.produkt.kaufeEin(eingekaufteMenge);

			int kunde_wants = curr.kunde.frageAn();
			int kunde_wants_and_gets = curr.produkt.frageAn(kunde_wants);

			double kosten_for_kunde = curr.produkt.verkaufe(kunde_wants_and_gets);
			double gewinn = curr.haendler.verbucheErgebnis(kosten_for_kunde, kosten_for_haenlder);


			String out = String.format("angefragte Menge: %d\n", eingekaufteMenge);
			out += String.format("verkaufte Menge: %d\n", kunde_wants_and_gets);
			out += String.format("Kosten: %.2f\n", kosten_for_haenlder);
			out += String.format("Umsatz: %.2f\n", kosten_for_kunde);
			out += String.format("Gewinn: %.2f\n", gewinn);
			out += String.format("Eigenkapital: %.2f\n", curr.haendler.liesEigenkapital());
			out += String.format("Lagerbestand: %d\n\n", curr.produkt.liesLagerbestand());
			
			if (curr.haendler.istPleite())
			{
				out += "Sie haben kein Eigenkapital mehr.\nEnde Ihres Unternehmens!";
				state.remove(event.event.getAuthor().getId());
			}
			
			event.commandSuccess(out);
		}
		
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%slagersimulation [count]' to play a simple game we made in computer class!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public String get_short_help()
	{
		return "Simple game we made in computer class";
	}

	@Override
	public void on_register()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		if (state.get(event.getUser().getId()) == null)
		{
			state.put(event.getUser().getId(), new DiscordState());
		}
		
		DiscordState curr = state.get(event.getUser().getId());
		
		int eingekaufteMenge = (int) event.getOption("count").getAsLong();

		double kosten_for_haenlder = curr.produkt.kaufeEin(eingekaufteMenge);

		int kunde_wants = curr.kunde.frageAn();
		int kunde_wants_and_gets = curr.produkt.frageAn(kunde_wants);

		double kosten_for_kunde = curr.produkt.verkaufe(kunde_wants_and_gets);
		double gewinn = curr.haendler.verbucheErgebnis(kosten_for_kunde, kosten_for_haenlder);


		String out = String.format("angefragte Menge: %d\n", eingekaufteMenge);
		out += String.format("verkaufte Menge: %d\n", kunde_wants_and_gets);
		out += String.format("Kosten: %.2f\n", kosten_for_haenlder);
		out += String.format("Umsatz: %.2f\n", kosten_for_kunde);
		out += String.format("Gewinn: %.2f\n", gewinn);
		out += String.format("Eigenkapital: %.2f\n", curr.haendler.liesEigenkapital());
		out += String.format("Lagerbestand: %d\n\n", curr.produkt.liesLagerbestand());
		
		if (curr.haendler.istPleite())
		{
			out += "Sie haben kein Eigenkapital mehr.\nEnde Ihres Unternehmens!";
			state.remove(event.getUser().getId());
		}
		
		event.reply(out).queue();
		
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("lagersimulation", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("count", "Wie viele produkte kaufen?", SlashCommandParameter.INTEGER, true)
		});

		try
		{
			reg.doRegister(Discord.discord.token, Discord.discord.application_id);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}

}
