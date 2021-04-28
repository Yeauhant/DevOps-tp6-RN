# DevOps-tp6-RN
La bibliothèque suivante permet de manipuler des ensembles de données sous forme de Dataframe.
Un Dataframe peut être créé directement, ou à partir d'un fichier. Un dataframe peut être affiché à l'écran. Des opérations sont disponibles : maximum/minimum d'une colonne, nombre d'éléments, moyenne. Des opérations de sélection sont disponibles, par ligne ou par colonne. On peut aussi sélectionner les élements d'une colonne avec une condition.

Ce projet a été réalisé sur Github. Nous utilisons JUnit pour tester notre code. Nous avons utilisé Maven pour compiler et tester le projet. Les GithubActions nous ont permis d'automatiser les test lors de pull request sur le dépôt. Un rapport de couverture du code peut être obtenu en exécutant "mvn jacoco:report".

Le workflow utilisé est le Gitflow. Les Pull Request sont validées (ou non) par un membre du groupe, après avoir vérifié le résultat du Workflow.

FEEDBACK : L'utilisation de Maven et de ses plugins était particulièrement compliquée. Les documentations sont peu claires.
