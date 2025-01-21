/**
 * @author Hassan Zeeshan
 * @version 7/23/2023
 * 
 * 	Summary: Overall, I didn’t find the project
 * 	to be too bad. There were certain methods that bugged me a little bit. For example, the pop (int k) method. 
 * 	I really sat there and thought it through, I thought about the different possibilities that could occur, 
 * 	then eventually, I got it. I was also stuck on the pathfinder class. My code just wasn’t functioning as 
 * 	intended (it was going through the dark chambers). After a while I found the error. I fixed the error and my code 
 * 	ran smoothly!
 */

// Importing necessary exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

public class PathFinder {
	// Initializing private instance variable
	private Map pyramidMap;

	/**
	 * Constructor method
	 * 
	 * @param fileName, taking in the name of the file the program will be using
	 */
	public PathFinder(String fileName) {
		Map map;
		try {
			// Setting pyramidMap to the map we are using (contains a description of the
			// park)
			map = new Map(fileName);
			pyramidMap = map;
		}
		// Catching potential issues and printing them to the screen
		catch (InvalidMapCharacterException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method which finds a path from the entrance to the treasure chambers which is
	 * reached by satisfying specific constraints
	 * 
	 * @return stack
	 */
	public DLStack<Chamber> path() {
		// Creating a new stack
		DLStack<Chamber> stack = new DLStack<Chamber>();
		// Finding entrance chamber
		Chamber enterence = pyramidMap.getEntrance();
		// Finding number of treasure chambers
		int numChambers = pyramidMap.getNumTreasures();
		// Creating a variable to count how many treasure chambers we've been to
		int numTreasure = 0;
		// Pushing and marking entrance chamber as pushed
		stack.push(enterence);
		enterence.markPushed();
		// While the stack isn't empty
		while (!stack.isEmpty()) {
			// Getting the current chamber
			Chamber currChamber = stack.peek();
			// If the current chamber is a treasure chamber we increment the counter
			if (currChamber.isTreasure()) {
				numTreasure++;
			}
			// If the current chamber is a treasure chamber and all treasure chambers have
			// been accessed...
			if (currChamber.isTreasure() == true && numTreasure == numChambers) {
				// Break out the loop
				break;
			}
			// Obtaining the best neighboring chamber
			Chamber best = bestChamber(currChamber);
			// If best chamber is not null
			if (best != null) {
				// Pushing the best chamber and marking it as pushed
				stack.push(best);
				best.markPushed();
			} else {
				// Otherwise we pop the chamber at the top of the stack and mark it as pushed
				Chamber popped = stack.peek();
				stack.pop();
				popped.markPopped();
			}
		}
		// Returning the stack
		return stack;
	}

	/**
	 * Method to return the value of pyramidMap
	 * 
	 * @return value of pyramid map
	 */
	public Map getMap() {
		return pyramidMap;
	}

	/**
	 * Method to check whether or not a chamber is dim
	 * 
	 * @param currentChamber, using the current chamber as a reference point
	 * @return if said chamber is dim or not
	 */
	public boolean isDim(Chamber currentChamber) {
		// Boolean used to check if any neighboring chambers are lighted
		boolean lighted = false;
		// Looping through the neighboring chambers
		for (int i = 0; i < 6; i++) {
			// If the neighboring chamber is not null
			if (currentChamber.getNeighbour(i) != null) {
				// And if the neighboring chamber is lighted...
				if (currentChamber.getNeighbour(i).isLighted()) {
					// Boolean is equal to true
					lighted = true;
				}
			}
		}
		// If the current chamber is not null, or sealed, or lighted, and a neighbor is
		// lighted then the current chamber is dim (return true)
		if (currentChamber != null && !currentChamber.isSealed() && !currentChamber.isLighted() && lighted == true) {
			return true;
		}
		return false;
	}

	/**
	 * Finds the best chamber to move to from the current chamber
	 * 
	 * @param currentChamber, the chamber in question
	 * 
	 * @return the best chamber/null
	 */
	public Chamber bestChamber(Chamber currentChamber) {
		// Looping through and checking if the neighbor is not null, not marked, and is
		// a treasure
		for (int i = 0; i < 6; i++) {
			// Creating a chamber 'n' and setting it to the neighbor
			Chamber n = currentChamber.getNeighbour(i);
			if (n != null && !n.isMarked() && n.isTreasure())
				// If the neighbor satisfies our search it is returned
				return n;
		}
		// Looping through and checking if the neighbor is not null, not marked, and is
		// lighted
		for (int i = 0; i < 6; i++) {
			Chamber n = currentChamber.getNeighbour(i);
			if (n != null && !n.isMarked() && n.isLighted())
				// If the neighbor satisfies our search, it is returned
				return n;
		}
		// Looping through and checking if the neighbor is not null, not marked, and is
		// dim
		for (int i = 0; i < 6; i++) {
			Chamber n = currentChamber.getNeighbour(i);
			if (n != null && !n.isMarked() && this.isDim(n))
				// If the neighbor satisfies our search, it is returned
				return n;
		}
		// Returning null
		return null;
	}
}