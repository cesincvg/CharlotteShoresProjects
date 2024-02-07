%Title: Tanom 
%Class: MA321
%Date: 10/6/2022

T=4;
e = 0.25;
n = 100;
tolb = exp(-12);
toln = exp(-12);
nmax = 150;

orbit = tanom(T, e, n, tolb, toln, nmax);



disp(orbit);
plot(orbit{:,5},orbit{:,6},"*")