type Props = {
	imgSrc?: string;
	text: string;
	visible: unknown;
};

export function EmptyState(props: Props) {
	const {visible, text} = props;

	if (!visible) {
		return null;
	}

	return (
		<div className='flex flex-col items-center justify-center p-4'>
			<div className='mb-4 h-10 w-10 bg-secondary-grey' />
			<div className='text-primary-grey'>{text}</div>
		</div>
	);
}
