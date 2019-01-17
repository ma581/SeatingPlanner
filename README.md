# Seating Planner
Creates an 'intelligent' seating plan for Zuhlke Camp

1. Reads a csv of peoples Names and Projects from the directory `/src/main/resources`. (eg `sample.csv` at the `/src/test/resources`)
2. The seating algorithm tries to iterate through each person, for each session, randomly allocating a table where they have not sat at previously.
3. It tries to allocate while constrained by the `number of people allowed at each table`, the `number of people of the same project allowed at each table` and a limit on the `max number of attempts` at optimising. 
