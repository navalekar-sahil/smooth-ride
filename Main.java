import java.util.ArrayList;
import java.util.Scanner;

class Car {
	private String carId;
	private String brand;
	private String carModel;
	private double basePrice;
	private boolean isAvailable;

	public Car(String carId, String brand, String carModel, double basePrice) {
		this.carId = carId;
		this.brand = brand;
		this.carModel = carModel;
		this.basePrice = basePrice;
		this.isAvailable = true;
	}

	public String getCarId() {
		return carId;
	}

	public String getBrand() {
		return brand;
	}

	public String getCarModel() {
		return carModel;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public double calculatePrice(double amount) {
		return basePrice * amount;
	}

	public boolean rent() {
		return isAvailable = false;
	}

	public boolean returnCar() {
		return isAvailable = true;
	}
}

class Customer {
	private String id;
	private String name;

	public Customer(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}

class Rental {
	private Car car;
	private Customer customer;
	private int days;

	public Rental(Car car, Customer customer, int days) {
		this.car = car;
		this.customer = customer;
		this.days = days;
	}

	public Car getCar() {
		return car;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getDays() {
		return days;
	}
}

class CarRentalSystem {
	ArrayList<Car> cars;
	ArrayList<Customer> customers;
	ArrayList<Rental> rentals;

	public CarRentalSystem() {
		cars = new ArrayList<>();
		customers = new ArrayList<>();
		rentals = new ArrayList<>();
	}

	public void addCar(Car car) {
		cars.add(car);
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public void rentCar(Car car, Customer customer, int days) {
		if (car.isAvailable()) {
			car.rent();
			rentals.add(new Rental(car, customer, days));
		} else {
			System.out.println("Car is not available.");
		}
	}

	public void returnCar(Car car) {
		car.returnCar();
		Rental rentalToRemove = null;
		for (Rental rental : rentals) {
			if (rental.getCar() == car) {
				rentalToRemove = rental;
			}
		}
		if (rentalToRemove != null) {
			rentals.remove(rentalToRemove);
		} else {
			System.out.println("car is not rented");
		}
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("\n========Car Rental System=======\n");
			System.out.println("1].rent a car\n2].Return a Car\n0].Exit");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter your name: ");
				String customerName = sc.next();

				System.out.println("<><><>Available Cars<><><>");
				for (Car car : cars) {
					if (car.isAvailable()) {
						System.out.println(car.getCarId() + "\t" + car.getCarModel() + "\t" + car.getBrand());
					}
				}

				System.out.println("Enter Car ID you want to rent :");
				String carId = sc.next();

				System.out.println("Enter Number of days for rental: ");
				int days = sc.nextInt();

				Customer newCustomer = new Customer("CUS" + (customers.size() + 10), customerName);
				customers.add(newCustomer);

				Car selectedCar = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && car.isAvailable()) {
						selectedCar = car;
						break;
					}
				}

				if (selectedCar != null) {
					double totalPrice = selectedCar.calculatePrice(days);

					System.out.println("\n== Rental Information ==\n");
					System.out.println("Customer ID: " + newCustomer.getId());
					System.out.println("Customer Name: " + newCustomer.getName());
					System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getCarModel());
					System.out.println("Rental Days: " + days);
					System.out.printf("Total Price: RS%.2f%n", totalPrice);
					sc.nextLine();
					System.out.println("Confirm Rental: (Y/N)");
					String confirm = sc.next();

					if (confirm.equalsIgnoreCase("Y")) {
						rentCar(selectedCar, newCustomer, days);
						System.out.println("Car Rent Successfully...");
					} else {
						System.out.println("Car Renting cancel");
					}
				} else {
					System.out.println("Car is not available for rent or id mismatch");
				}
				break;

			case 2:
				System.out.println("Enter Car ID that you want to return: ");
				String carID = sc.next();

				Car carToReturn = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carID)) {
						carToReturn = car;
						break;
					}
				}
				if (carToReturn != null) {
					Customer customer = null;
					for (Rental rental : rentals) {
						if (rental.getCar().equals(carToReturn)) {
							customer = rental.getCustomer();
							break;
						}
					}
					if (customer != null) {
						returnCar(carToReturn);
						System.out.println("Car returned successfully by " + customer.getName());
					} else {
						System.out.println("Car was not rented or rental information is missing");
					}
				} else {
					System.out.println("Invalid car iD or car is not rented");
				}
				break;

			case 0:
				System.out.println("Thank you for visiting Car Rental System");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid choice. Please enter a valid option.");
				break;
			}
		}
	}
}

public class Main {
	public static void main(String[] args) {
		CarRentalSystem rentalSystem = new CarRentalSystem();

		Car car1 = new Car("C001", "Toyota", "Camry", 1000);
		Car car2 = new Car("C002", "Honda", "Accord", 1500);
		Car car3 = new Car("C003", "Mahindra", "Thar", 2000.0);
		rentalSystem.addCar(car1);
		rentalSystem.addCar(car2);
		rentalSystem.addCar(car3);

		rentalSystem.menu();
	}
}