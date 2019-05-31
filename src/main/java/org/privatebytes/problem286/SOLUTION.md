### Skyline solution

The problem can be solved as follows:
1. initialize the skyline: iterate over all buildings and collect in a SortedMap all the points where a building starts or ends (key = X coordinate, value = 0)
2. update heights in the skyline: 
   - iterate over all buildings 
   - in each iteration update in the list from #1 step all the coordinates between the start and end of the current building with the max value between height of the current building and existing value in the map.
