import {ReactNode} from 'react';
import {cn} from '@shared/lib';

type Props = {
	children: ReactNode;
	className?: string;

	withMediumVertical?: unknown;
	withMediumTop?: unknown;
	withMediumBottom?: unknown;

	withBaseVertical?: unknown;
	withBaseTop?: unknown;
	withBaseBottom?: unknown;
	withTitleBottom?: unknown;

	withBaseHorizontal?: unknown;
};

export function Box(props: Props) {
	const {
		children,
		className,

		withMediumVertical,
		withMediumTop,
		withMediumBottom,

		withBaseVertical,
		withBaseTop,
		withBaseBottom,
		withTitleBottom,

		withBaseHorizontal,
	} = props;

	return (
		<div
			className={cn(
				className,

				withMediumVertical && 'py-6',
				withMediumTop && 'pt-6',
				withMediumBottom && 'pb-6',

				withBaseVertical && 'py-4',
				withBaseTop && 'pt-4',
				withBaseBottom && 'pb-4',
				withTitleBottom && 'pb-3',

				withBaseHorizontal && 'px-4',
			)}
		>
			{children}
		</div>
	);
}
