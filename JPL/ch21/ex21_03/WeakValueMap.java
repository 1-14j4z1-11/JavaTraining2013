package ex21_03;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class WeakValueMap<K, V> implements Map<K, V>
{
	private HashMap<K, WeakReference<V>> map = new HashMap<K, WeakReference<V>>();
	private Set<V> tmpValues = null;
	
	@Override
	public void clear()
	{
		this.map.clear();
	}

	@Override
	public boolean containsKey(Object key)
	{
		this.captureValues();
		boolean ret = this.map.containsKey(key);
		this.releaseValues();
		
		return ret;
	}

	@Override
	public boolean containsValue(Object value)
	{
		WeakReference<Object> ref = new WeakReference<>(value);

		this.captureValues();
		boolean ret = this.map.containsKey(ref);
		this.releaseValues();
		
		return ret;
	}

	@Override
	public Set<Entry<K, V>> entrySet()
	{
		Set<Entry<K, V>> set = new HashSet<Entry<K,V>>();
		
		this.captureValues();
		
		for(K key : this.map.keySet())
		{
			set.add(new SimpleEntry<>(key, this.map.get(key).get()));
		}
		
		this.releaseValues();
		
		return set;
	}

	@Override
	public V get(Object key)
	{
		this.captureValues();
		
		WeakReference<V> ref = this.map.get(key);
		V ret = (ref == null) ? null : ref.get();
		
		this.releaseValues();
		
		return ret;
	}

	@Override
	public boolean isEmpty()
	{
		this.captureValues();
		boolean ret = this.map.isEmpty();
		this.releaseValues();
		
		return ret;
	}

	@Override
	public Set<K> keySet()
	{
		this.captureValues();
		Set<K> ret = this.map.keySet();
		this.releaseValues();
		
		return ret;
	}

	@Override
	public V put(K key, V value)
	{
		WeakReference<V> ref = this.map.put(key, new WeakReference<V>(value));
		
		return (ref == null) ? null : ref.get();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map)
	{
		for(Entry<? extends K, ? extends V> e : map.entrySet())
		{
			this.map.put(e.getKey(), new WeakReference<V>(e.getValue()));
		}
	}

	@Override
	public V remove(Object key)
	{
		this.captureValues();
		V ret = this.map.remove(key).get();
		this.releaseValues();
		
		return ret;
	}

	@Override
	public int size()
	{
		this.captureValues();
		int ret = this.map.size();
		this.releaseValues();
		
		return ret;
	}

	@Override
	public Collection<V> values()
	{
		this.captureValues();
		
		Collection<V> ret = new HashSet<>();
		
		for(K key : this.map.keySet())
		{
			ret.add(this.map.get(key).get());
		}
		
		this.releaseValues();
		
		return ret;
	}
	
	/**
	 * 参照先が存在する値への強参照を作成し、参照先が存在しない値に対応するKeyを削除する
	 */
	private void captureValues()
	{
		this.tmpValues = new HashSet<>();
		Set<K> removeKeys = new HashSet<K>();
		
		for(K key : this.map.keySet())
		{
			if(this.map.get(key).get() == null)
			{
				removeKeys.add(key);
			}
			else
			{
				this.tmpValues.add(this.map.get(key).get());
			}
		}
		
		for(K key : removeKeys)
		{
			this.map.remove(key);
		}
	}
	
	/**
	 * captureValues()で生成した値への強参照を削除する
	 */
	private void releaseValues()
	{
		this.tmpValues.clear();
		this.tmpValues = null;
	}
}
