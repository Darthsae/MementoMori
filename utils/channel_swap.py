from PIL import Image
from argparse import ArgumentParser, Namespace

def SwapChannels(a_imagePath: str, a_channelOrder: tuple[int, int, int, int], a_savePath: str) -> None:
    with Image.open(a_imagePath) as image:
        channels: tuple[Image.Image, Image.Image, Image.Image, Image.Image] = image.convert("RGBA").split()
        
        Image.merge("RGBA", [channels[i] for i in a_channelOrder]).save(a_savePath)

if __name__ == "__main__":
    parser: ArgumentParser = ArgumentParser(prog='ChannelSwap')
    parser.add_argument('-i', '--input', type=str)
    parser.add_argument('-o', '--output', type=str)
    parser.add_argument('--channels', type=int, nargs=4)
    args: Namespace = parser.parse_args()
    SwapChannels(args.input, args.channels, args.output)