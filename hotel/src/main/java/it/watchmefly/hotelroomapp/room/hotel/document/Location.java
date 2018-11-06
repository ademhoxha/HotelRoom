package it.watchmefly.hotelroomapp.room.hotel.document;

public class Location {
	
	private String city;
	private String state;
	private String coordinates;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	public Location(String city, String state, String coordinates) {
		super();
		this.city = city;
		this.state = state;
		this.coordinates = coordinates;
	}
	public Location() {
		super();
	}
	@Override
	public String toString() {
		return "Location [city=" + city + ", state=" + state + ", coordinates=" + coordinates + "]";
	}

}
