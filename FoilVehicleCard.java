package a12132881;

import java.util.*;

public class FoilVehicleCard extends VehicleCard {
	
	private Set<Category> specials;

	public FoilVehicleCard(final String name, final Map<Category, Double> categories, final Set<Category> specials) {
		super(name, categories);
		if (specials == null || specials.size() > 3 || specials.isEmpty())
			throw new IllegalArgumentException("illegal specials");
		for (Category help : specials)
			if (help == null)
				throw new IllegalArgumentException("specials contains null");
		Set<Category> shallow = new HashSet<>(specials);
		this.specials = shallow;
		
	}
	
	public Set<Category> getSpecials() {
		Set<Category> shallow = new HashSet<>(this.specials);
		return shallow;
	}
	
	@Override
	public int totalBonus() {
		int sum = super.totalBonus();
		Map<Category, Double> categ = super.getCategories();
		for (Category spec : specials) {
			for (Map.Entry<Category, Double> entry : categ.entrySet()) {
				if (entry.getKey() == spec)
					sum += Math.abs(entry.getValue());
			}
		}
		return sum;
	}
	
	@Override
	public String toString() { 
		List<String> list = new ArrayList<>();
		Map<Category, Double> help = this.getCategories();
		for (Map.Entry<Category, Double> entry : help.entrySet()) {
			boolean check = false;
			for (Category spec : specials) {
				if (entry.getKey() == spec)
					check = true;
			}
			if (check == true)
				list.add("*" + entry.getKey() + "*" + "=" + entry.getValue());
			else 
				list.add(entry.getKey() + "=" + entry.getValue());
		}
		String out = String.join(", ", list);
		return "- " + super.getName() + "(" + this.totalBonus() + ")" + " -> {" + out + "}";
	}

}
