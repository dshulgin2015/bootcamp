library(igraph)
edges <- read.table("~/bootcamp/task3/output.txt")
g <- graph.data.frame(edges, directed = TRUE)
g <- simplify(g)
E(g)$color <- "grey"
plot(g, vertex.size=2, vertex.label=NA, layout=layout.kamada.kawai,
edge.width=2)

