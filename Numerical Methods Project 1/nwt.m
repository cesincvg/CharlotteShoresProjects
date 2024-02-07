%Title: Newton Method
%Class: MA321
%Date: 10/6/2022


function [root, found, iter] = nwt(p0,toln,nmax,func,dfunc)
    error = toln;
    iter = 0;
    found = false;

    if (func(p0) == 0)
        root = p0;
        return;
    end
    
   
    while (iter < nmax)
        p1 = p0 - func(p0)/dfunc(p0);
        error = abs(p1-p0);
        root = p1;

        iter = iter+1;

        if (error <= toln)
            found = true;
            return;
        else
            found = false;
        end
        p0 = p1;
    end
end
