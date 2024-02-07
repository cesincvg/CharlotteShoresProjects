%Title: Bisection Method
%Class: MA321
%Date: 10/1/2022

function[root, a, b]=bis(a, b, tolb, func)  
        funcA = func(a);
        funcB = func(b);

        nmax = 150;

        if(sign(funcA) == sign(funcB))
            disp("same signs for A and B")
            return;
        end
        
        error = b-a;

        for n=1:nmax
                error = error/2;
                root = a + error;
                funcR = func(root);
                if(abs(error) < tolb)
                    return;
                end

              
                if sign(funcA) == sign(funcR)
                    a = root;
                    funcA = func(root);
                else
                    b = root;
                    funcB = func(root);
                end

        end
    end









