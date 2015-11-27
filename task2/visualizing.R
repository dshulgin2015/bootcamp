library(igraph)
edges <- read.table("~/bootcamp/task2/output.txt")
g <- graph(edges, directed = FALSE)
g <- simplify(g)g
E(g)$color <- "grey"
plot(g, vertex.size=2, vertex.label=NA, layout=layout.kamada.kawai,
edge.width=2)

