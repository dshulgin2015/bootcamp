library(igraph)
edges <- read.table("~/bootcamp/task1/output.txt")
g <- graph.data.frame(edges, directed = FALSE)
g <- simplify(g)
E(g)$color <- "grey"
plot(g, vertex.size=2, vertex.label=NA, layout=layout.kamada.kawai,
edge.width=1, edge.arrow.size=0.2)

