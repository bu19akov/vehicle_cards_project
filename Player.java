package a12132881;

import java.util.*;
import static java.util.Comparator.comparing;

public class Player implements Comparable <Player>{
	private String name;
	private Queue <VehicleCard> deck = new ArrayDeque <>();
	
	public Player(final String name) {
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("illegal name");
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		int sum = 0;
		for (VehicleCard help : deck) {
			sum += help.totalBonus();
		}
		return sum;
	}
	
	public void addCards(final Collection <VehicleCard> cards) {
		if (cards != null && !cards.contains(null))
			for (VehicleCard help : cards)
				deck.add(help);
		else
			throw new IllegalArgumentException("cards == null");
	}
	  
	public void addCard(final VehicleCard card) {
		if (card != null)
			deck.add(card);
		else
			throw new IllegalArgumentException("card == null");
	}
	
	public void clearDeck () {
		deck.clear();
	}
	
	public List<VehicleCard> getDeck() {
		List<VehicleCard> copy = new ArrayList<>();
		for (VehicleCard help : deck)
			copy.add(help);
		return copy;
	}
	
	protected VehicleCard peekNextCard() {
		if (this.deck.isEmpty())
			return null;
		else 
			return ((ArrayDeque<VehicleCard>) this.deck).getFirst();
	} 
	
	public VehicleCard playNextCard() {
		if (this.deck.isEmpty())
			return null;
		else 
			return ((ArrayDeque<VehicleCard>) this.deck).removeFirst();
	}
	
	@Override
	public int compareTo(final Player other) {
		return this.getName().compareTo(other.getName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name.toUpperCase());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return name.equalsIgnoreCase(other.name);
	}
	
	@Override
	public String toString() {
		List<String> list = new ArrayList<>();
		for (VehicleCard card : deck) {
			list.add(card.toString());
		}
		String out = String.join("\n", list);
		return name + "(" + this.getScore() + "):\n" + out;
	}
	
	public boolean challengePlayer(Player p) {
		if (p == null || p == this)
			throw new IllegalArgumentException("p is null or this");
		List<VehicleCard> this_help = this.getDeck();
		List<VehicleCard> p_help = p.getDeck();
		VehicleCard p1 = this.peekNextCard();
		VehicleCard p2 = p.peekNextCard();
		List <VehicleCard> win = new ArrayList<>();
		do {
			if (this.getDeck().isEmpty() || p.getDeck().isEmpty()) {
				this.clearDeck();
				this.addCards(this_help);
				p.clearDeck();
				p.addCards(p_help);
				return false;
			}
			else {
				p1 = this.playNextCard();
				win.add(p1);
				p2 = p.playNextCard();
				win.add(p2);
			}
		} while (p1.compareTo(p2) == 0);
		if (p1.compareTo(p2) > 0) {
			this.addCards(win);
			return true;
		}
		else if (p1.compareTo(p2) < 0) {
			p.addCards(win);
		}
		return false;
	}
	public static Comparator<Player> compareByScore() {
		return comparing(Player::getScore);
	}
	
	public static Comparator<Player> compareByDeckSize() {
		return Comparator.comparingInt(p -> p.getDeck().size());
	}
}
