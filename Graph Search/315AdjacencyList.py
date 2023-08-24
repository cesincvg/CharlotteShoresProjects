from collections import defaultdict
from collections import deque
import sys

#the code below demonstartes how to print an adjecency list for the vertices and edgespowe
class Node :
    def __init__(self, name : str, id_num : int) :
        self.name = name
        self.id_num = id_num

    def __repr__(self) :
        return self.name + " " + str(self.id_num)

class Graph :

    def __init__(self, nodes : int) :
        self.adjlist = defaultdict(list)
        self.nodes = nodes

    def AddEdge (self, src_node : Node(str,int), dst_node : Node(str,int)) :

        self.adjlist[src_node].append(dst_node)
        self.adjlist[dst_node].append(src_node)

    def Display_AdjList (self) :
        for item in self.adjlist.items() :
            print (str(item))
            
    

#The information below is how the files are being read in 

if __name__ == "__main__":
    file = open("RomaniaEdges.txt", "r")

    edgespowerList = [ ]
    edges = file.readlines()

#clean all of my lines

    for i in range(23):
        edges[i] = edges[i].rstrip("\n")
        edges[i] = edges[i].split(",")
        edges[i][0] = edges[i][0]
        edges[i][1] = (edges[i][1])
        edges[i][2] = int(edges[i][2])
        edgespowerList.append(edges[i][0])
        edgespowerList.append(edges[i][1])
        edgespowerList.append(edges[i][2])


    print("This is the edges printed out in order")
    print(" ")
    
    for i in range(69):
        print(edgespowerList[i])
        
        
    file = open("RomaniaVertices.txt", "r")
    
    verticesPowerList = [ ]
    vertices = file.readlines()
    
    for i in range (20):
        vertices[i] = vertices[i].rstrip("\n")
        verticesPowerList.append(vertices[i])
    
    print("This is the vertices printed out in order")
    print(" ")
    
    for i in range(20):
        print(verticesPowerList[i])
        
    #we will write code to create the graph and edges now
    nodes = 20
    
     
    g = Graph(nodes)
    g.AddEdge("Arad", "Zerind")
    g.AddEdge("Arad" , "Timisoara")
    g.AddEdge("Arad" , "Sibiu")
    g.AddEdge("Bucharest", "Urziceni")
    g.AddEdge("Bucharest", "Giurgiu")
    g.AddEdge("Bucharest", "Pitesti")
    g.AddEdge("Bucharest", "Fagaras")
    g.AddEdge("Craiova", "Dobreta")
    g.AddEdge("Craiova", "Pitesti")
    g.AddEdge("Craiova", "RimnicuVilcea")
    g.AddEdge("Dobreta", "Mehadia")
    g.AddEdge("Eforie", "Hirsova")
    g.AddEdge("Fagaras", "Sibiu")
    g.AddEdge("Hirsova", "Urziceni")
    g.AddEdge("Iasi", "Neamt")
    g.AddEdge("Lugoj", "Mehadia")
    g.AddEdge("Lugoj", "Timisoara")
    g.AddEdge("Oradea", "Zerind")
    g.AddEdge("Oradea", "Sibiu")
    g.AddEdge("Pitesti", "RimnicuVilcea")
    g.AddEdge("RimnicuVilcea", "Sibiu")
    g.AddEdge("Urziceni", "Vaslui")
    
    
    print("")
    print("We will now display the adjacency list")
    print("")
    
    g.Display_AdjList()
    
    
    
    
    
    
        
        
   
    
        
        