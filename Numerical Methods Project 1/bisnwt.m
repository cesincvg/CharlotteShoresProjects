%Title: Bisection Newton Method
%Class: MA321
%Date: 10/6/2022

function [root, iter, itern] = bisnwt(a,b,tolb,toln,nmax,func,dfunc)
    error = abs(b-a);
    iter = 0;
    itermax = 10^4;

    while ((error > toln) && (iter <= itermax))
            [p, a, b] = bis(a, b, tolb, func);
            [root, error, itern] = nwt(p, toln, nmax, func, dfunc);
             if (error > toln)
                 tolb = tolb / 2;
             else
                 disp(root);
                 break;
             end
    iter = iter + 1;
    end
    disp('Method Failed to acheieve error <= toln');
    return;
end