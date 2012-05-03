/*
 * Tectonicus-mods, http://github.com/pkmnfrk/tectonicus-mods
 * 
 * This file based on source from Tectonicus:
 * 
 * Source code from Tectonicus, http://code.google.com/p/tectonicus/
 *
 * Tectonicus is released under the BSD license (below).
 *
 *
 * Original code John Campbell / "Orangy Tang" / www.triangularpixels.com
 *
 * Copyright (c) 2012, John Campbell
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this list
 *     of conditions and the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above copyright notice, this
 *     list of conditions and the following disclaimer in the documentation and/or
 *     other materials provided with the distribution.
 *   * Neither the name of 'Tecctonicus' nor the names of
 *     its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package com.mikecaron.tectoniucs.ic2;

import tectonicus.BlockContext;
import tectonicus.BlockType;
import tectonicus.BlockTypeRegistry;
import tectonicus.blockTypes.BlockUtil;
import tectonicus.rasteriser.Mesh;
import tectonicus.raw.RawChunk;
import tectonicus.renderer.Geometry;
import tectonicus.texture.SubTexture;
import tectonicus.util.Colour4f;

public class IC2Machine implements BlockType
{
	private final String name;
	
	private final SubTexture bottomTexture;
	private final SubTexture topTexture;
	private final SubTexture leftTexture;
	private final SubTexture frontTexture;
	private final SubTexture rightTexture;
	private final SubTexture backTexture;
	
	private Colour4f colour;

	public IC2Machine(String name, SubTexture bottom, SubTexture top, SubTexture left, SubTexture front, SubTexture right, SubTexture back)
	{
		this.name = name;
		
		this.bottomTexture = bottom;
		this.topTexture = top;
		this.leftTexture = left;
		this.frontTexture = front;
		this.rightTexture = right;
		this.backTexture = back;
		
		colour = new Colour4f(1, 1, 1, 1);
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public boolean isSolid()
	{
		return true;
	}
	
	@Override
	public boolean isWater()
	{
		return false;
	}
	
	@Override
	public void addInteriorGeometry(int x, int y, int z, BlockContext world, BlockTypeRegistry registry, RawChunk rawChunk, Geometry geometry)
	{
		addEdgeGeometry(x, y, z, world, registry, rawChunk, geometry);
	}
	
	@Override
	public void addEdgeGeometry(int x, int y, int z, BlockContext world, BlockTypeRegistry registry, RawChunk chunk, Geometry geometry)
	{
		Mesh bottomMesh = geometry.getMesh(bottomTexture.texture, Geometry.MeshType.Solid);
		Mesh topMesh = geometry.getMesh(topTexture.texture, Geometry.MeshType.Solid);
		Mesh leftMesh = geometry.getMesh(leftTexture.texture, Geometry.MeshType.Solid);
		Mesh frontMesh = geometry.getMesh(frontTexture.texture, Geometry.MeshType.Solid);
		Mesh rightMesh = geometry.getMesh(rightTexture.texture, Geometry.MeshType.Solid);
		Mesh backMesh = geometry.getMesh(backTexture.texture, Geometry.MeshType.Solid);
		
		final int data = chunk.getBlockData(x, y, z);
		
		SubTexture northTex = null;
        SubTexture southTex = null;
		SubTexture eastTex = null;
        SubTexture westTex = null;
		
		Mesh northMesh = null;
		Mesh southMesh = null;
		Mesh eastMesh = null;
		Mesh westMesh = null;
		
		/*if(data == 0x4) {
			northTex = frontTexture; northMesh = frontMesh;
			southTex = backTexture;  southMesh = backMesh;
			eastTex = rightTexture;  eastMesh = rightMesh;
			westTex = leftTexture;   westMesh = leftMesh;
		} else if(data == 0x5) {
			northTex = backTexture;  northMesh = backMesh;
			southTex = frontTexture; southMesh = frontMesh;
			eastTex = leftTexture; 	 eastMesh = leftMesh;
			westTex = rightTexture;  westMesh = rightMesh;
		} else if(data == 0x2) {
			eastTex = frontTexture;  eastMesh = frontMesh;
			westTex = backTexture; 	 westMesh = backMesh;
			northTex = leftTexture;  northMesh = leftMesh;
			southTex = rightTexture; southMesh = rightMesh;
		} else if(data == 0x3) {
			eastTex = backTexture; 	 eastMesh = backMesh;
			westTex = frontTexture;  westMesh = frontMesh;
			northTex = rightTexture; northMesh = rightMesh;
			southTex = leftTexture;  southMesh = leftMesh;
		}*/
		
		//unlike vanilla blocks, direction is determined by a tile entity.
		//let's just make them face the camera for now
		northTex = backTexture;  northMesh = backMesh;
		southTex = frontTexture; southMesh = frontMesh;
		eastTex = leftTexture; 	 eastMesh = leftMesh;
		westTex = rightTexture;  westMesh = rightMesh;
		
		
		BlockUtil.addTop(world, chunk, topMesh, x, y, z, colour, topTexture, registry);
		BlockUtil.addBottom(world, chunk, bottomMesh, x, y, z, colour, bottomTexture, registry);
		
		BlockUtil.addNorth(world, chunk, northMesh, x, y, z, colour, northTex, registry);
		BlockUtil.addSouth(world, chunk, southMesh, x, y, z, colour, southTex, registry);
		BlockUtil.addEast(world, chunk, eastMesh, x, y, z, colour, eastTex, registry);
		BlockUtil.addWest(world, chunk, westMesh, x, y, z, colour, westTex, registry);
	}
}
