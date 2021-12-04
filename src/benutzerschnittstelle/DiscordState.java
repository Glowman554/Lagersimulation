package benutzerschnittstelle;

import fachkonzept.Haendler;
import fachkonzept.Kunde;
import fachkonzept.Produkt;

public class DiscordState
{
	public Haendler haendler;
	public Kunde kunde;
	public Produkt produkt;
	
	public DiscordState()
	{
		haendler = new Haendler();
		kunde = new Kunde();
		produkt = new Produkt(1.50, 2.00);
	}
}
