import {type CarouselProps, CarouselRef, Slider} from './ui/Slider.tsx';
import {useRef} from 'react';

export function useSlider() {
	const carouselRef = useRef<CarouselRef>(null);

	return {
		Slider: (props: CarouselProps) => <Slider ref={carouselRef} {...props} />,
		handleSlideRight: () => carouselRef.current?.handleRightClick(),
		handleSlideLeft: () => carouselRef.current?.handleLeftClick(),
	};
}
