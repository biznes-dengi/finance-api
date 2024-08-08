import {MutableRefObject, ReactNode, useEffect, useRef} from 'react';
import {Drawer as VaulDrawer} from 'vaul';

import {cn} from '@shared/lib';

const {Root, Trigger, Close, Overlay, Content, Portal} = VaulDrawer;

type UseDrawerProps = {
	openDrawerRef: MutableRefObject<() => void>;
	closeDrawerRef: MutableRefObject<() => void>;
};

export type DrawerProps = {
	children: ReactNode;
	afterAutoCloseAction?: () => void;
	isCloseDisabled?: boolean;
	withOverlay?: boolean;
	isFullScreen?: boolean;
	direction?: 'top' | 'bottom' | 'left' | 'right';
};

export function Drawer(props: UseDrawerProps & DrawerProps) {
	const {
		openDrawerRef,
		closeDrawerRef,
		children,
		afterAutoCloseAction,
		isCloseDisabled,
		direction,
		withOverlay = true,
		isFullScreen,
	} = props;

	const withAutoClose = !!afterAutoCloseAction;

	const openButtonRef = useRef<HTMLButtonElement>(null);
	const closeButtonRef = useRef<HTMLButtonElement>(null);

	function openDrawer() {
		openButtonRef.current && openButtonRef.current.click();

		if (withAutoClose) {
			setTimeout(closeDrawer, 2000);
			setTimeout(afterAutoCloseAction, 2500);
		}
	}
	function closeDrawer() {
		closeButtonRef.current && closeButtonRef.current.click();
	}

	useEffect(() => {
		openDrawerRef.current = openDrawer;
		closeDrawerRef.current = closeDrawer;
	}, []);

	return (
		<Root dismissible={!isCloseDisabled} direction={direction}>
			<Trigger ref={openButtonRef} className='hidden' />
			<Close ref={closeButtonRef} className='hidden' />

			<Portal>
				<Overlay className={cn('fixed inset-0', withOverlay && 'bg-black/40')} />

				<Content
					className={cn(
						'fixed bottom-0 left-0 right-0 flex flex-col rounded-t-2xl bg-white transition-all duration-300',
						isFullScreen ? 'h-[96%]' : 'max-h-[96%]',
						direction === 'right' && 'h-full rounded-none',
					)}
				>
					<div className='mx-auto flex w-full max-w-md flex-col overflow-auto rounded-t-[10px] p-4'>
						{withAutoClose ? (
							<div className='text-center'>animated progress</div>
						) : (
							<div className='mx-auto mb-6 h-1.5 w-12 rounded-full bg-zinc-300' />
						)}

						{children}
					</div>
				</Content>
			</Portal>
		</Root>
	);
}
