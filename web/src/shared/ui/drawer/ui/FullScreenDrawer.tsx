import {Drawer} from 'vaul';

export function FullScreenDrawer() {
	return (
		<Drawer.Root shouldScaleBackground>
			<Drawer.Trigger asChild>
				<button>Open FullScreenDrawer</button>
			</Drawer.Trigger>
			<Drawer.Portal>
				<Drawer.Overlay className='fixed inset-0 bg-black/40' />
				<Drawer.Content className='fixed bottom-0 left-0 right-0 mt-24 flex h-[96%] flex-col rounded-t-[10px] bg-zinc-100'>
					<div className='flex-1 rounded-t-[10px] bg-white p-4'>
						<div className='mx-auto mb-8 h-1.5 w-12 flex-shrink-0 rounded-full bg-zinc-300' />
						<div className='mx-auto max-w-md'>
							<Drawer.Title className='mb-4 font-medium'>Unstyled drawer for React.</Drawer.Title>
							<p className='mb-2 text-zinc-600'>
								Lorem ipsum dolor sit amet, consectetur adipisicing elit. A ab accusamus atque cum deserunt distinctio
								doloremque dolorum, ea eligendi hic id natus perspiciatis, praesentium quae qui quidem repudiandae rerum
								voluptatibus? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam asperiores, autem
								corporis cupiditate ea enim illo itaque laborum necessitatibus neque, possimus rem. Accusantium eum
								explicabo harum, nulla perferendis qui sequi.
							</p>
						</div>
					</div>
				</Drawer.Content>
			</Drawer.Portal>
		</Drawer.Root>
	);
}
