/**
 * Main class to execute the program for navigating through the magical map.
 * This program reads data from input files, processes it using the Graph class,
 * and calculates the shortest path to complete objectives provided in the mission file.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        // Input files containing node, edge, and mission data
        File file0 = new File(args[0]);
        File file1 = new File(args[1]);
        File file2 = new File(args[2]);

        // Scanners to read input files
        Scanner landScanner = new Scanner(file0);
        Scanner travelScanner = new Scanner(file1);
        Scanner missionScanner = new Scanner(file2);

        // Output file to store the results
        FileWriter fw = new FileWriter(args[3]);
        BufferedWriter outputFile = new BufferedWriter(fw);

        // Read the dimensions of the grid from the nodes file
        int rows = landScanner.nextInt();
        int cols = landScanner.nextInt();

        // Initialize the graph with given rows and columns
        Graph graph = new Graph(rows, cols);

        // Read node information and add them to the graph
        while (landScanner.hasNext()) {
            int x = landScanner.nextInt();
            int y = landScanner.nextInt();
            int type = landScanner.nextInt();
            graph.addNode(x, y, type);
        }

        // Read edge information and add travel times to the graph
        while (travelScanner.hasNext()) {
            String[] part1 = travelScanner.next().split(",");
            String[] position0 = part1[0].split("-");
            String[] position1 = part1[1].split("-");
            double time = Double.parseDouble(travelScanner.next());
            int x1 = Integer.parseInt(position0[0]);
            int y1 = Integer.parseInt(position0[1]);
            int x2 = Integer.parseInt(position1[0]);
            int y2 = Integer.parseInt(position1[1]);
            graph.addTravelTime(x1, y1, x2, y2, time);
        }

        // Read the visibility radius from the mission file
        int radius = Integer.parseInt(missionScanner.nextLine());
        graph.radius=radius;

        // Read the starting point coordinates
        String[] startingpoints=missionScanner.nextLine().split(" ");
        int startX = Integer.parseInt(startingpoints[0]);
        int startY = Integer.parseInt(startingpoints[1]);

        // Variables to track objectives and wizard's choices
        int count=1;
        String[] options=null;
        ArrayList<String> prevselections=new ArrayList<>();

        // Process each objective in the mission file
        while (missionScanner.hasNextLine()) {
            String[] line=missionScanner.nextLine().split(" ");
            int targetX = Integer.parseInt(line[0]);
            int targetY = Integer.parseInt(line[1]);

            // Handle wizard's choices if options are provided
            if(options!=null){
                double checkshortestpath=Double.MAX_VALUE;
                String checkshtortestpath_s="";
                for(String s:options){
                    if(prevselections.contains(s)){
                        continue;
                    }
                    graph.types[Integer.parseInt(s)].t=0;
                    double d=graph.dijkstra(startX,startY,targetX,targetY,new ArrayList<>());
                    graph.types[Integer.parseInt(s)].t=Integer.parseInt(s);
                    if(d<checkshortestpath){
                        checkshortestpath=d;
                        checkshtortestpath_s=s;
                    }
                }
                prevselections.add(checkshtortestpath_s);
                outputFile.write("Number "+checkshtortestpath_s+" is chosen!");
                outputFile.newLine();
                graph.types[Integer.parseInt(checkshtortestpath_s)].t=0;
                options=null;
            }

            // Parse wizard's options if present
            if(line.length>2){
                options=new String[line.length-2];
                for(int i=2; i<line.length;i++){
                    options[i-2]=line[i];
                }
            }

            // Update visibility for the current position
            graph.updateVisibility(startX,startY,radius,new ArrayList<>());

            // Loop to calculate and traverse the shortest path to the target
            boolean loopcondition=true;
            while(loopcondition){
                ArrayList<Node> path=new ArrayList<>();
                graph.dijkstra(startX,startY,targetX,targetY,path);
                for(int i=0;i< path.size();i++){
                    outputFile.write("Moving to "+path.get(i).x+"-"+path.get(i).y);
                    outputFile.newLine();
                    boolean bl=graph.updateVisibility(path.get(i).x,path.get(i).y,radius,path);
                    if(!bl){
                        startX=path.get(i).x;
                        startY=path.get(i).y;
                        outputFile.write("Path is impassable!");
                        outputFile.newLine();
                        break;
                    }
                    if(path.get(i).x==targetX&&path.get(i).y==targetY){
                        outputFile.write("Objective "+count+" reached!");
                        outputFile.newLine();
                        count+=1;
                        loopcondition=false;
                    }

                }
            }

            // Update the starting point for the next objective
            startX = targetX;
            startY = targetY;

        }

        // Close all resources
        landScanner.close();
        travelScanner.close();
        missionScanner.close();
        outputFile.close();
        fw.close();
    }
}
