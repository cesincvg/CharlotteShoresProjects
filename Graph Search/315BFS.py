# finds shortest path between 2 cities in project 3
def bfs_shortest_path(graph, start, goal):
    print("Starting from:  " + start  + "  The goal is: " + goal)
    # keep track of explored nodes
    explored = []
    # aths to be checked
    queue = [[start]]

    # keeps looping until all possible paths have been checked
    while queue:
        # pop the first path from the queue
        path = queue.pop(0)
        print(path)
        # get the last node from the path
        node = path[-1]
        if node not in explored:
            neighbours = graph[node]
            # go through all neighbour nodes
            for neighbour in neighbours:
                new_path = list(path)
                new_path.append(neighbour)
                queue.append(new_path)
                # return path if neighbour is goal
                if neighbour == goal:
                    return new_path
            explored.append(node)
    
    print(new_path)
 
    return 
 

# sample graph implemented as a dictionary
graph = {'Arad': ['Zerind', 'Timisoara', 'Sibiu'],
        'Zerind': ['Arad', 'Oradea'],
        'Timisoara': ['Arad', 'Lugoj'],
        'Sibiu': ['Arad', 'Fagaras', 'Oradea', 'RimnicuVilcea'],
        'Bucharest': ['Urziceni', 'Giurgiu', 'Pitesti', 'Fagaras'],
        'Urziceni': ['Bucharest', 'Hirsova', 'Vaslui'],
        'Giurgiu': ['Bucharest'],
        'Pitesti': ['Bucharest', 'Craiova', 'RimnicuVilcea'],
        'Fagaras': ['Bucharest', 'Sibiu'],
        'Craiova': ['Dobreta', 'Pitesti', 'RimnicuVilcea'],
        'Dobreta': ['Craiova', 'Mehadia'],
        'RimnicuVilcea': ['Craiova', 'Pitesti', 'Sibiu'],
        'Mehadia': ['Dobreta', 'Lugoj'],
        'Eforie': ['Hirsova'],
        'Hirsova': ['Eforie', 'Urziceni'],
        'Iasi': ['Neamt'],
        'Neamt': ['Iasi'],
        'Lugoj': ['Mehadia', 'Timisoara'],
        'Oradea': ['Zerind', 'Sibiu'],
        'Vaslui': ['Urziceni']}
        
print("Arad to Sibui")
print("")
bfs_shortest_path(graph, 'Arad', 'Sibiu')

print("")

print("Arad to Craiva")
print("")
bfs_shortest_path(graph, 'Arad', 'Craiova')

print("Arad to Bucharest")
print("")
bfs_shortest_path(graph, 'Arad', 'Bucharest')
