### 3 Wheels Lock

The problem can be solved as follows:
1. generate a graph with all nodes from 000 to 999 except the deadends
2. each node is connected to at most 5 other nodes. example: 000 is connected with 001, 010, 100, 900, 090, 009
3. find shortest path from 000 to target combination