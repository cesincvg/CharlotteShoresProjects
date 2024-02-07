%Title: Tanom 
%Class: MA321
%Date: 10/6/2022

function orbit = tanom(T,e,n,varargin)
    %  T: the period of the orbit in hours
    %  e: the eccentricity of the orbit.
    %  n: the number of points where we want to localize the satellite on its orbit.
    Variables = {'t', 'E', 'v', 'r,', 'x', 'y' };
    orbitmatrix = [];
    tolb = varargin{1};
    toln = varargin{2};
    nmax = varargin{3};

    a = 0;
    b = 2*pi;
    
    for i=0:n
        %solving for t
        ti = (i*T)/n;

        %solve for M
        M = 2*(pi)*(ti/T);
       
        %Kepler's
        kepler = @(E)E-e*sin(E)-M;

        %derivative
        keplerd = @(E) 1-(e*cos(E));


       
        [E, iter, itern] = bisnwt(0, 2*pi, tolb,toln, nmax, kepler, keplerd);


        vt = acos((e-cos(E))/(e*cos(E)-1));
        %constant for mew
        mew = 3.986012 * 10^5;
        alpha = (mew * (T/(2*pi))^2)^(1/3);

        

        if ((E >= pi) && (E <= 2*pi))
            vt = vt + pi;
        end

        r = (alpha *(1-e^2))/(1+e*cos(vt));
        
        xt = r * cos(vt);
        yt = r * sin(vt);

        orbitmatrix = [orbitmatrix; ti E vt r xt yt];

        fprintf("i1 = %f TANOM B\n", i);


    end
    orbit = array2table(orbitmatrix);
    orbit.Properties.VariableNames(1:size(orbitmatrix, 2)) = Variables
end