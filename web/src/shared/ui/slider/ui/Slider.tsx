import {
	Children,
	cloneElement,
	forwardRef,
	ReactElement,
	useEffect,
	useImperativeHandle,
	useRef,
	useState,
} from 'react';

import {cn} from '@shared/lib';

export type CarouselProps = {
	children: ReactElement[];
	activeSlideIndex?: number;
};

export type CarouselRef = {
	handleLeftClick: () => void;
	handleRightClick: () => void;
};

export const Slider = forwardRef<CarouselRef, CarouselProps>((props, ref) => {
	const {children, activeSlideIndex} = props;

	const [offset, setOffset] = useState(0);
	const [slides, setSlides] = useState<ReactElement[]>([]);
	const [slideWidth, setSlideWidth] = useState<number>(0);
	const [withTransition, setWithTransition] = useState(false);

	const slideRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		// To get dynamic block width

		if (!slideRef.current) return;

		setSlideWidth(slideRef.current.clientWidth);
	}, []);
	useEffect(() => {
		// To make slides equal width

		if (!slideWidth) return;

		const mappedChildren = Children.map(children, (child) => {
			return cloneElement(child, {style: {minWidth: slideWidth, maxWidth: slideWidth}});
		});

		setSlides(mappedChildren);
	}, [slideWidth]);
	useEffect(() => {
		// To show first slide -> slides[activeSlideIndex]

		if (!activeSlideIndex || !slideWidth) return;

		Array.from({length: activeSlideIndex}).forEach(() => {
			setOffset((currentOffset) => currentOffset - slideWidth);
		});
	}, [slideWidth]);

	function handleLeftClick() {
		if (!withTransition) setWithTransition(true);
		setOffset((currentOffset) => Math.min(currentOffset + slideWidth, 0));
	}
	function handleRightClick() {
		if (!withTransition) setWithTransition(true);
		setOffset((currentOffset) => {
			const maxOffset = -(slideWidth * (slides.length - 1));
			return Math.max(currentOffset - slideWidth, maxOffset);
		});
	}

	useImperativeHandle(ref, () => ({
		handleLeftClick,
		handleRightClick,
	}));

	return (
		<div className={cn('flex w-full')} ref={slideRef}>
			<div className='w-full overflow-hidden'>
				<div
					className={cn('flex', withTransition && 'transition-transform duration-500 ease-in-out')}
					style={{transform: `translateX(${offset}px)`}}
				>
					{slides}
				</div>
			</div>
		</div>
	);
});

/** Tech debt
 * too many rerenders
 * */
