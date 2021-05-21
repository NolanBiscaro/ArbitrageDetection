# ArbitrageDetection

Completed as part of a NETS 150 final project. Detects arbitrage between foreign currenices. 

Using the Bellman-Ford algorithm, we are able to detect negative edge weight cycles in a graph. Using the currencies as vertices and the rates between them as edges, 
we can find an arbitrage opporutnity. Specifically, if we traverse a series of edges and arrive at the original node with a net profit (positive value), we have found
arbitrage. Since Bellman-Ford finds negative edge weight cycles, we can simply negate the edge weights and find the negative (positive) edge weight cycle. 

Example: CAD, USD, AUD, EUR where 1 USD buys 0.75 AUD, 1 AUD buys 150.6 CAD, 1 CAD buys 12 EUR, and 1 EUR buys 0.0009 USD

then 0.75 * 150.6 * 12 * 0.0009 = 1.21 = 21% profit. 

In general we are looking for x1 * x2 * .... * x_n > 1

Note that Bellman-Ford sums the edge weights, therefore we can take the log of both sides to get x1 + x2 + ... + x_n > 0

Negating the weights gives x1 + x2 + ... + x_n < 0 (negative cycle) therefore Bellman-Ford can be used. 
