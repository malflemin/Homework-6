/*
Name: Malik Fleming
Deadline: Friday, November 22 at 10:00am
Course: CSCE 4430.001

Write a Java program equivalent to the Python program at:

https://github.com/ptarau/CrackerBarrelPegPuzzle

that implements a solver for the Cracker Barrel Peg Puzzle at:

https://shop.crackerbarrel.com/toys-games/games/travel-games/peg-game/606154

This time you will need to use classes and objects for things like the moves, the board you display them on and the solver itself.

Post your answer on github.com and provide a link to it in canvas. You can reuse the restepsitory created for the same assignment in Prolog or create a new one.

 */
import java.util.*;
import static java.lang.System.*;

public class pegsolver{

 final public static int PEGDIM = 5;

 public static int[] movesX = {
  -1,
  -1,
  0,
  0,
  1,
  1
 };
 public static int[] movesY = {
  -1,
  0,
  -1,
  1,
  0,
  1
 };
 public static int PEGR = 4;
 public static int PEGC = 2;



 public static HashMap <Integer,Integer> ranges;

 public static boolean masked(int bitMov, int bit) {
  return ((1 << bit)&bitMov) != 0;
 }

 public static boolean ranging(int onR, int onC) {
  return onR < PEGDIM && onR >= 0 && onC <= onR && onC >= 0;
 }
 
 public static void main(String[] args) {
  int begin = beginGame(PEGR, PEGC);

  ranges = new HashMap<Integer,Integer>();
  ranges.put(begin, 0);

  LinkedList<Integer> pegList = new LinkedList<Integer>();
  pegList.offer(begin);

  do{
   int current = pegList.poll();
   int currentdist = ranges.get(current);

   ArrayList <Integer> arrList = nextstep(current);
   for (int i = 0; i < arrList.size(); i++) {
    if (!ranges.containsKey(arrList.get(i))) {
     ranges.put(arrList.get(i), currentdist + 1);
     pegList.offer(arrList.get(i));
    }
   }
   if (arrList.size() == 0) display(current);
  }while (pegList.size() > 0);
  
 }
 public static int beginGame(int pegR, int pegC) {
  int bitMov = 0;
  
  for (int i = 0; i < PEGDIM; i++)
   for (int j = 0; j <= i; j++)
    bitMov = bitMov | (1 << (PEGDIM * i + j));

  return bitMov - (1 << (PEGDIM * pegR + pegC));
 }
 public static ArrayList<Integer> nextstep(int bitMov) {

  ArrayList<Integer> steps = new ArrayList<Integer>();
  for (int r = 0; r < PEGDIM; r++) {
   for (int c = 0; c <= PEGDIM; c++) {
    for (int i = 0; i < movesX.length; i++) {
     if (!ranging(r + PEGC * movesX[i], c + PEGC * movesY[i])) 
		 continue;
     if (masked(bitMov, PEGDIM * r + c) && masked(bitMov, PEGDIM * (r + movesX[i]) + 
        c + movesY[i]) && !masked(bitMov, PEGDIM *
       (r + PEGC * movesX[i]) + c + PEGC * movesY[i])) {
		   
      int step = addstep(bitMov, i, r, c);
      steps.add(step);
	  
     }
    }
   }
  }

  return steps;
 }

 public static void display(int bitMov) {

  for (int i = 0; i < PEGDIM; i++) {
   for (int j = 0; j < PEGDIM - 1 - i; j++) 
		out.print(" ");
   for (int j = 0; j <= i; j++) {
    if (masked(bitMov, PEGDIM * i + j)) 
		out.print("x ");
    else 
		out.print("* ");
   }
   out.println();
  }
  out.println();
 }

 public static int addstep(int bitMov, int i, int r, int c) {
  int begin = PEGDIM * r + c;
  int middle = PEGDIM * (r + movesX[i]) + c + movesY[i];
  int end = PEGDIM * (r + PEGC * movesX[i]) + c + PEGC * movesY[i];

  return bitMov 
          + (1 << end) 
          - (1 << middle) 
          - (1 << begin);
 }
}