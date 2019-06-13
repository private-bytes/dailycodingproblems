### Solution

In order to rezolve this problem, we have to find the index in the array of stones where we can insert the tallest pyramid.
Once we know the height of the pyramid we can compute its value (number of stones required to construct it): `pyramidValue = maxHeight + maxHeight*(maxHeight-1)`
The cost to make the pyramid will be: `cost = <sum of all stones> - pyramidValue`

To find the pyramid maxHeight and the index in the array where it can be inserted we can use 2 variables to keep track of the pyramid start and end position.
Another variable will be used to track the pyramid height.
We start at position 0: `startIdx = 0, endIdx = 0, height=1`
We increase the endIdx, go uphill, as long as the stone at endIdx is high enough. If a stone is too short, we start to go downhill untill we finish the pyramid.