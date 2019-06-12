package SF.Transforms;

import SF.Signal;

public interface Transform {

    Signal transform(Signal signal);
    Signal inverseTransform(Signal signal);
}
