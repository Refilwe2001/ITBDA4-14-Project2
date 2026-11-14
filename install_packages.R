# Project 2 - R packages used in Questions 2 to 4
pkgs <- c("tidyverse","tidytext","tm","topicmodels","textstem","wordcloud","RColorBrewer",
          "slam","reshape2","rvest","robotstxt","textrank","lexRankr","igraph","ggraph",
          "tokenizers","stringi","rmarkdown","knitr")
missing <- setdiff(pkgs, rownames(installed.packages()))
if (length(missing)) install.packages(missing, dependencies = TRUE)
